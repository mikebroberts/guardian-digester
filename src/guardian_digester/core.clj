(ns guardian-digester.core
	(:require [clj-http.client :as client])
	(:use hiccup.core))

; ** For retrieving from Guardian API

(def endpoints ["" "world" "uk" "business" "sport/cricket" "film" "commentisfree" 
	"science" "society" "politics" "environment" "media" "money"])

(defn make-url [endpoint] 
	(str "http://content.guardianapis.com/" endpoint "?format=json&show-editors-picks=true&show-fields=trailText,standfirst"))

(defn urls []
	(map make-url endpoints))

(defn query [url]
	(:body (client/get url {:as :json})))

(defn query-all [urls]
	(map query urls))

; ** Rendering

(defn render-story [{{trailText :trailText} :fields :keys [webTitle webUrl]}]
	[:li [:a {:href webUrl} webTitle] " : " trailText])

(defn render-section [[section items]] 
	[[:h2 section] [:ul (map render-story items)]])

(defn render-stories [by-section]
	(str "<!DOCTYPE html>\n" (html [:html 
		[:head
			[:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}] 
			[:link {:href "http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" :rel "stylesheet" :media "screen"}]]
		[:body 
			[:div (mapcat render-section by-section)]
		]])))

(defn xform-story-responses [response-list]
	(->> response-list
 		(mapcat #(get-in % [:response :editorsPicks])) ; get the :response -> :editorsPicks value in each response, and merge
 		(group-by :id) (vals) (map first) ; remove duplicate stories
 		(group-by :sectionName)
 	))

(defn render [stories]
	(-> stories (xform-story-responses) (render-stories)))

; ** 

(defn query-guardian-and-render []
	(println "query-guardian-and-render")
	(-> (urls) (query-all) (render)))
(ns guardian-digester.handler
	(:use compojure.core [guardian-digester.core :only (query-guardian-and-render)])
	(:require [compojure.handler :as handler]
            [compojure.route :as route]))

(def cache (atom {:data "" :time 0}))

(defn foo [m f timeout]
	(let [now (System/currentTimeMillis)]
		(if (< (+ (:time m) timeout) now)
			{:time now :data (f)}
			m)))

(defn lookup [f timeout]
	(:data (swap! cache foo f timeout)))

(defroutes app-routes
	(GET "/" [] (lookup query-guardian-and-render 300000)) ; five minutes
	(route/not-found "Not Found"))

(def app
  (handler/site app-routes))
(ns guardian-digester.handler
	(:use compojure.core [guardian-digester.core :only (query-guardian-and-render)])
	(:require [compojure.handler :as handler]
            [compojure.route :as route]
            [noir.util.cache :as cache]))

(cache/set-timeout! 300) ; 5 minute cache

(defroutes app-routes
	(GET "/" [] (cache/cache! :home (query-guardian-and-render)))
	(route/not-found "Not Found"))

(def app
  (handler/site app-routes))
(ns guardian-digester.handler
	(:use compojure.core [guardian-digester.core :only (query-guardian-and-render)])
	(:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
	(GET "/" [] (query-guardian-and-render))
	(route/not-found "Not Found"))

(def app
  (handler/site app-routes))
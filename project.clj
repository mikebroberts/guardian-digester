(defproject guardian-digester "0.1.0-SNAPSHOT"
	:description "Queries The Guardian Newspaper's Content API to give a digest of current headlines"
	:url "https://github.com/mikebroberts/guardian-digester"
	:license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
 	:dependencies [
  		[org.clojure/clojure "1.4.0"] 
	  	[clj-http "0.7.0"]
  		[hiccup "1.0.3"]
  		[org.clojure/data.json "0.2.1"]
  		[compojure "1.1.5"]]
	:plugins [[lein-ring "0.8.2"]]
	:ring {:handler guardian-digester.handler/app}
	:profiles {:dev {:dependencies [[ring-mock "0.1.3"]]}}
	:min-lein-version "2.0.0" ; required for Heroku
)

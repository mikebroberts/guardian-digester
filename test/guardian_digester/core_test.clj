(ns guardian-digester.core-test
	(:require [clojure.data.json :as json])
 	(:use clojure.test
        guardian-digester.core
        hiccup.core))

(defn load-from-file []
	(clojure.walk/keywordize-keys (json/read-str (slurp "test/guardian_digester/example.json"))))

(defn write-to-file [x]
	(spit "test.html" x))

(defn run-file-to-file []
	(write-to-file (make-html (load-from-file))))

(defn r []
	(make-html (load-from-file)))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
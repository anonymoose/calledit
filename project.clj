(defproject calledit "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "A wee calledit project demonstrating the (very) basics of Compojure and Ring"
  :url "http://adambard.com/blog/Getting-started-with-Clojure-web-apps/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring "1.1.8"]
                 [ring.middleware.logger "0.4.0"]  ; https://github.com/pjlegato/ring.middleware.logger
                 [compojure "1.1.5"]
                 [de.ubercode.clostache/clostache "1.3.1"]]
  :main calledit.core)
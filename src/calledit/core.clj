(ns calledit.core
  (:use [compojure.core]
        [ring.middleware.params         :only [wrap-params]]
        [ring.middleware.cookies        :only [wrap-cookies]]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        )
  (:require
    [ring.adapter.jetty :as jetty]
    [clostache.parser :as clostache]
    [compojure.route :as route]
    [ring.middleware.logger :as logger]
    [ring.util.response]
    [clojure.tools.logging :as log]
    [clojure.java.io]))


;; Template Rendering
(defn read-template [template-name]
  (slurp (clojure.java.io/resource
    (str "templates/" template-name ".html"))))

(defn render-template [template-file params]
   (clostache/render (read-template template-file) params))

;; View functions
(defn index [msg asdf]
  (log/info "ken")
  (render-template "index" {:greeting msg, :fud asdf}))

;; Routing
(defroutes app-routes
  (GET "/" [msg asdf] (index msg asdf))
  (route/resources "/")
  (route/not-found "404 Not Found"))

(defn app []
  (-> app-routes
      (logger/wrap-with-logger)
      (wrap-cookies)
      (wrap-keyword-params)
      (wrap-params)))

(defn svr-start []
    (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty (app) {:port port :join? false})))


(defn -main [] (svr-start))









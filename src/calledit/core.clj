(ns calledit.core
  (:use [compojure.core]
        [ring.middleware.params         :only [wrap-params]]
        [ring.middleware.cookies        :only [wrap-cookies]]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [calledit.controller.routes :only [app-routes]]
        [calledit.lib.db :only [connect-db]]
        )
  (:require
    [ring.adapter.jetty :as jetty]
    [ring.middleware.logger :as logger]
    [ring.util.response]
    [clojure.tools.logging :as log]))


(defn app []
  (-> app-routes
      (logger/wrap-with-logger)
      (wrap-cookies)
      (wrap-keyword-params)
      (wrap-params)))


(defn svr-start []
  (connect-db)
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty (app) {:port port :join? false})))


(defn -main [] (svr-start))









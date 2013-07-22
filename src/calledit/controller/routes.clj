;; 
;; Compojure Routing
;;

(ns calledit.controller.routes
  (:use
   [compojure.core]
   [clojure.tools.logging :only (info error)]) 
  (:require
   [compojure.route :as route]
   [calledit.controller.site]
   ))


(defroutes app-routes
  (GET "/" [] (calledit.controller.site/index))
  (GET "/call-edit" [] (calledit.controller.site/call-edit))
  (GET "/call-edit/:call_id" [call_id] (calledit.controller.site/call-edit call_id))
  (POST "/call-create" [email prediction prediction_dt] (calledit.controller.site/call-save email prediction prediction_dt))
  (POST "/call-save"   [call_id email prediction prediction_dt] (calledit.controller.site/call-save email prediction prediction_dt call_id))
  (GET "/find" [email]          (calledit.controller.site/call-find email))
  (GET "/about" []          (calledit.controller.site/about))
  (GET "/:call_id" [call_id] (calledit.controller.site/call-view call_id)) ; must be last

  (route/resources "/")
  (route/not-found "404 Not Found"))

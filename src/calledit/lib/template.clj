;;
;; Clostache Template Rendering
;;
(ns calledit.lib.template
  (:require
   [clojure.java.io]
   [clostache.parser :as clostache]))


(defn read-template [template-name]
  (slurp (clojure.java.io/resource
    (str "templates/" template-name ".html"))))


(defn render-template [template-file params]
   (clostache/render (read-template template-file) params))


(defn render-page
  [template data partials]
  "Pass in the template name (a string, sans its .html
    filename extension), the data for the template (a map), and a list of
    partials (keywords) corresponding to like-named template filenames."
  (clostache/render-resource
    (str "templates/" template ".html")
    data
    (reduce (fn [accum pt] ;; "pt" is the name (as a keyword) of the partial.
              (assoc accum pt (slurp (clojure.java.io/resource (str "templates/"
                                                                    (name pt)
                                                                    ".html")))))
            {}
            partials)))
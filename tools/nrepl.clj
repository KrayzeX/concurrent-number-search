(ns nrepl
  (:require
   [nrepl.server :as server]
   [nrepl.middleware :as mw]
   [cider.nrepl :as cipl]
   [refactor-nrepl.middleware :as refactor]))

(def handler
  (let [mws
        (->> cipl/cider-middleware
             (map resolve)
             (concat server/default-middlewares))
        stack
        (->> (conj mws #'refactor/wrap-refactor)
             mw/linearize-middleware-stack
             reverse
             (apply comp))]
    (stack server/unknown-op)))

(declare server)

(defn -main [& args]
  (def server (server/start-server :port 7999 :handler handler))
  (println "Server started at port" 7999))

(comment
  (server/stop-server server))


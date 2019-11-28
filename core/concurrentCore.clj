(ns concurrentCore
  (:require [clojure.core.async :refer [>! <! >!! <!! go chan close!]]))

(def generate-random-number
  (partition 20 (take 100 (repeatedly #(rand-int 1000)))))

(defonce msg-chan (chan))
(defonce answer-chan (chan))

(defn search-number [array number]
  (let [array-part-1 (nth generate-random-number 0)
        array-part-2 (nth generate-random-number 1)
        array-part-3 (nth generate-random-number 2)
        array-part-4 (nth generate-random-number 3)
        array-part-5 (nth generate-random-number 4)]
    (go
      (loop []
         (if (= (<!! msg-chan) "I found an element!")
           (print "I am a process № 1 and I finished.")
           (do
             (if (some #(= number %) array-part-5)
               (do (>!! msg-chan "I found an element!")
                   (let [index-of-number (.indexOf array-part-5 number)]
                     (>!! answer-chan (str "The index of found number is: " index-of-number))))
               (print "I am a process № 1 and I haven't element in my array's part!"))
             (recur)))))

    (go
      (loop []
        (if (= (<!! msg-chan) "I found an element!")
          (print "I am a process № 2 and I finished.")
          (do
            (if (some #(= number %) array-part-5)
              (do (>!! msg-chan "I found an element!")
                  (let [index-of-number (.indexOf array-part-5 number)]
                    (>!! answer-chan (str "The index of found number is: " index-of-number))))
              (print "I am a process № 2 and I haven't element in my array's part!"))
            (recur)))))

    (go
      (loop []
        (if (= (<!! msg-chan) "I found an element!")
          (print "I am a process № 3 and I finished.")
          (do
            (if (some #(= number %) array-part-5)
              (do (>!! msg-chan "I found an element!")
                  (let [index-of-number (.indexOf array-part-5 number)]
                    (>!! answer-chan (str "The index of found number is: " index-of-number))))
              (print "I am a process № 3 and I haven't element in my array's part!"))
            (recur)))))

    (go
      (loop []
        (if (= (<!!) "I found an element!")
          (print "I am a process № 4 and I finished.")
          (do
            (if (some #(= number %) array-part-5)
              (do (>!! msg-chan "I found an element!")
                  (let [index-of-number (.indexOf array-part-5 number)]
                    (>!! answer-chan (str "The index of found number is: " index-of-number))))
              (print "I am a process № 4 and I haven't element in my array's part!"))
            (recur)))))

    (go
      (loop []
        (if (= (<!! msg-chan) "I found an element!")
          (print "I am a process № 5 and I finished.")
          (do
            (if (some #(= number %) array-part-5)
              (do (>!! msg-chan "I found an element!")
                  (let [index-of-number (.indexOf array-part-5 number)]
                    (>!! answer-chan (str "The index of found number is: " index-of-number))))
              (print "I am a process № 5 and I haven't element in my array's part!"))
            (recur)))))

    (println (<!! answer-chan))))

#_(println  (contains? (nth generate-random-number 0) 5))

;; (search-number generate-random-number 50)

(defn gen-numbers []
  (->> #(rand-int 1000)
       repeatedly
       (take 1000000000)
       (partition 2000)))

(defn find-number [x number-seq]
  (->> number-seq
       (map-indexed vector)
       (filter first)
       first
       second))

;; (time (->> (pmap #(find-number 267 %) (gen-numbers))
;;            (remove nil?)
;;            first))

;; (time (->> (map #(find-number 267 %) (gen-numbers))
;;            (remove nil?)
;;            first))

;; (->> (pmap #(find-number 267 %) (gen-numbers))
;;      (remove nil?)
;;      first
;;      (println str "Index of found number is:"))

(defn -main [& args]

  ;; (time (->> (pmap #(find-number 267 %) (gen-numbers))
  ;;            (remove nil?)
  ;;            first))

  ;; (time (->> (map #(find-number 267 %) (gen-numbers))
  ;;            (remove nil?)
  ;;            first))

  ;; (->> (pmap #(find-number 267 %) (gen-numbers))
  ;;      (remove nil?)
  ;;      first
  ;;      (println str "Index of found number is:"))

   (search-number generate-random-number 134)
  )

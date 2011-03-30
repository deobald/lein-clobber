(ns leiningen.clobber.test.clobber
  (:use [leiningen.clobber] :reload)
  (:use [clojure.test]
        [clojure.contrib.io :only [file]])
  (:require [clojure.contrib.duck-streams :as duck]))

(def the-project {:root "."
                  :name "test"
                  :compile-path "classes"})

(deftest clobbers-files
  (duck/spit "to-delete.txt" "hi")
  (clobber (assoc the-project :clobber ["to-delete.txt"]))
  (is (not (.exists (file "to-delete.txt")))))


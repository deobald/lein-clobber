(ns leiningen.clobber "Delete files and directories"
    (:use [leiningen.clean]
          [lancet.core :only [delete fileset]]
          [clojure.contrib.io :only [file]]))

(defn dir? [f]
  (.isDirectory (file f)))

(defn dirs-only [clobberables]
  (filter dir? clobberables))

(defn files-only [clobberables]
  (filter (complement dir?) clobberables))

(defn- clobber-dirs [files]
  (doseq [to-delete (dirs-only files)]
    (delete {:dir to-delete})))

(defn- clobber-files [files]
  (let [to-delete (apply str (interpose "," (files-only files)))]
    (delete {} (fileset {:dir "." :includes to-delete}))))

(defn clobber [project & args]
  (let [files (:clobber project)]
    (clobber-files files)
    (clobber-dirs files)
    (clean project)))

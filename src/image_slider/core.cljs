(ns image-slider.core
  "This namespace contains the application and is the entrypoint for 'yarn start'."
  (:require [reagent.core :as r]
            [image-slider.app :as app]))

(defn ^:dev/after-load render
  "Render the toplevel component for this app."
  []
  (r/render
    [app/core]
    (.getElementById js/document "app")))

(defn ^:export main
  "Run application startup logic."
  []
  (render))
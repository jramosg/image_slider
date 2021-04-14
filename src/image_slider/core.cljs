(ns image-slider.core
  "This namespace contains your application and is the entrypoint for 'yarn start'."
  (:require [reagent.core :as r]
            [image-slider.hello :refer [app]]))

(defn ^:dev/after-load render
      "Render the toplevel component for this app."
      []
      (r/render [app] (.getElementById js/document "app")))

(defn ^:export main
  "Run application startup logic."
  []
  (render))

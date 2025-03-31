package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
)

type RequestData struct {
	Value int `json:"value"`
}

func processValue(w http.ResponseWriter, r *http.Request) {
	var data RequestData

	if err := json.NewDecoder(r.Body).Decode(&data); err != nil {
		http.Error(w, "Invalid request", http.StatusBadRequest)
		return
	}

	fmt.Printf("Received value: %d\n", data.Value)

	w.WriteHeader(http.StatusOK)
	w.Write([]byte(fmt.Sprintf("Processed value: %d", data.Value)))
}

func main() {
	http.HandleFunc("/api/process", processValue)
	log.Fatal(http.ListenAndServe(":8080", nil))
}

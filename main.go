package main

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()

	r.POST("/webhook", func(c *gin.Context) {
		// Handle the message
		var webhookEvent map[string]interface{}
		if err := c.BindJSON(&webhookEvent); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		if entry, exists := webhookEvent["entry"].([]interface{}); exists {
			for _, e := range entry {
				if messaging, exists := e.(map[string]interface{})["messaging"].([]interface{}); exists {
					for _, m := range messaging {
						if message, exists := m.(map[string]interface{})["message"].(map[string]interface{}); exists {
							if text, exists := message["text"].(string); exists {
								fmt.Println("Received message:", text) // Print the message to console
							}
						}
					}
				}
			}
		}
		c.String(200, "EVENT_RECEIVED")
	})

	r.GET("/webhook", func(c *gin.Context) {
		if c.DefaultQuery("hub.verify_token", "") == "hi" {
			c.String(http.StatusOK, c.DefaultQuery("hub.challenge", ""))
			return
		}
		c.String(http.StatusForbidden, "Error, wrong validation token")
	})

	r.Run(":8080")
}

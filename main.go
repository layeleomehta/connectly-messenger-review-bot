package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()

	r.POST("/webhook", func(c *gin.Context) {
		// Handle the message
		c.String(http.StatusOK, c.)

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

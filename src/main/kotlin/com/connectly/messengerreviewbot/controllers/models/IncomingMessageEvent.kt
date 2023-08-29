package com.connectly.messengerreviewbot.controllers.models

import com.fasterxml.jackson.annotation.JsonProperty

data class IncomingMessageEvent(
    @JsonProperty("object")
    val obj: String,
    val entry: List<Entry>
)

data class Entry(
    val id: String,
    val time: Int,
    val messaging: List<Messaging>
)

data class Messaging(
    val sender: Sender,
    val recipient: Recipient,
    val timestamp: Int,
    val message: Message
)

data class Sender(
    val id: String
)

data class Recipient(
    val id: String
)

data class Message(
    val mid: String,
    val text: String
)
//{
//    "object": "page",
//    "entry": [
//    {
//        "id": "112810605251603",
//        "time": 1693273305347,
//        "messaging": [
//        {
//            "sender": {
//            "id": "6456535891062838"
//        },
//            "recipient": {
//            "id": "112810605251603"
//        },
//            "timestamp": 1693273305043,
//            "message": {
//            "mid": "m_kIOH_0L_3BNAcXkPz1yFK6RrcqT1Hk_0J6AskfsiOUPnYioxxSxzH2_KTxrwPjUi10DV8zITRLcNdGd-z4w7kA",
//            "text": "laye is cool"
//        }
//        }
//        ]
//    }
//    ]


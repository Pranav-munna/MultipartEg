package com.twixttechnologies.multiparteg.Controller

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder


/**
 * @author Pranav Ashok on 11/06/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("status", "message")
class Responce {

    @JsonProperty("status")
    @get:JsonProperty("status")
    var status: String? = null

    @JsonProperty("message")
    @get:JsonProperty("message")
    var message: String? = null

}
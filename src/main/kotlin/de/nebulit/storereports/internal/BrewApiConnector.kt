package de.nebulit.storereports.internal

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

data class Brewery(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("brewery_type") val breweryType: String,
    @JsonProperty("address_1") val address1: String,
    @JsonProperty("address_2") val address2: String?,
    @JsonProperty("address_3") val address3: String?,
    @JsonProperty("city") val city: String,
    @JsonProperty("state_province") val stateProvince: String,
    @JsonProperty("postal_code") val postalCode: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("longitude") val longitude: String,
    @JsonProperty("latitude") val latitude: String,
    @JsonProperty("phone") val phone: String?,
    @JsonProperty("state") val state: String?,
    @JsonProperty("street") val street: String?
)

@Component
class BrewApiConnector(val restTemplate: RestTemplate = RestTemplate()) {

  val URL: String = "https://api.openbrewerydb.org/v1/breweries?by_country={country}"

  fun requestBrewery(country: String): List<Brewery> {
    val result =
        restTemplate
            .exchange(
                URL,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<Brewery?>?>() {},
                country)
            .body ?: emptyList<Brewery>()
    return result.filterNotNull()
  }
}

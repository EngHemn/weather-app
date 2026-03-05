package com.example.wheatherapp.domain.utils

/**
 * Utility class for temperature conversions.
 * The API provides temperatures in Celsius by default.
 */
object TemperatureConverter {

    /**
     * Convert Celsius to Fahrenheit
     * Formula: (°C × 9/5) + 32
     *
     * @param celsius Temperature in Celsius
     * @return Temperature in Fahrenheit
     */
    fun celsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9.0 / 5.0) + 32.0
    }

    /**
     * Convert Fahrenheit to Celsius
     * Formula: (°F - 32) × 5/9
     *
     * @param fahrenheit Temperature in Fahrenheit
     * @return Temperature in Celsius
     */
    fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32.0) * 5.0 / 9.0
    }

    /**
     * Convert temperature to desired unit
     *
     * @param temperature Temperature value in Celsius (API default)
     * @param unit Target unit - "CELSIUS" or "FAHRENHEIT"
     * @return Converted temperature, rounded to 2 decimal places
     */
    fun convertTemperature(temperature: Double, unit: String): Double {
        return if (unit == "FAHRENHEIT") {
            (celsiusToFahrenheit(temperature) * 100).toInt() / 100.0
        } else {
            (temperature * 100).toInt() / 100.0
        }
    }

    /**
     * Get temperature symbol for the given unit
     *
     * @param unit Temperature unit - "CELSIUS" or "FAHRENHEIT"
     * @return Temperature symbol
     */
    fun getTemperatureSymbol(unit: String): String {
        return if (unit == "FAHRENHEIT") "°F" else "°C"
    }
}

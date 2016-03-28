package de.alferink.beelogger

class SensorType {

    String name
    SensorUnit unit

    static constraints = {
    }
}

enum SensorUnit {
    DEGREE_CELSIUS,
    RELATIVE_HUMIDITY
}

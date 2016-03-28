package de.alferink.beelogger

class Sensor {

    String name
    SensorType type

    static belongsTo = [beehive: Beehive]
    static hasMany = [measurements: Measurement]

    static constraints = {
    }
}

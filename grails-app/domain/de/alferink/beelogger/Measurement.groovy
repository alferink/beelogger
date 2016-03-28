package de.alferink.beelogger

class Measurement {

    Date date
    Double value

    static belongsTo = [sensor: Sensor]

    static constraints = {
    }
}

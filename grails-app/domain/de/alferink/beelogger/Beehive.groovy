package de.alferink.beelogger

class Beehive {

    String name

    static hasMany = [sensors: Sensor]

    static constraints = {
    }
}

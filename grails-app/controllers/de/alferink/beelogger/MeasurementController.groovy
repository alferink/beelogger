package de.alferink.beelogger

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MeasurementController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Measurement.list(params), model:[measurementCount: Measurement.count()]
    }

    def show(Measurement measurement) {
        respond measurement
    }

    def create() {
        respond new Measurement(params)
    }

    @Transactional
    def save(Measurement measurement) {
        if (measurement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (measurement.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond measurement.errors, view:'create'
            return
        }

        measurement.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'measurement.label', default: 'Measurement'), measurement.id])
                redirect measurement
            }
            '*' { respond measurement, [status: CREATED] }
        }
    }

    def edit(Measurement measurement) {
        respond measurement
    }

    @Transactional
    def update(Measurement measurement) {
        if (measurement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (measurement.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond measurement.errors, view:'edit'
            return
        }

        measurement.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'measurement.label', default: 'Measurement'), measurement.id])
                redirect measurement
            }
            '*'{ respond measurement, [status: OK] }
        }
    }

    @Transactional
    def delete(Measurement measurement) {

        if (measurement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        measurement.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'measurement.label', default: 'Measurement'), measurement.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

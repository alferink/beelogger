package de.alferink.beelogger

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BeehiveController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Beehive.list(params), model:[beehiveCount: Beehive.count()]
    }

    def show(Beehive beehive) {
        respond beehive
    }

    def create() {
        respond new Beehive(params)
    }

    @Transactional
    def save(Beehive beehive) {
        if (beehive == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (beehive.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond beehive.errors, view:'create'
            return
        }

        beehive.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'beehive.label', default: 'Beehive'), beehive.id])
                redirect beehive
            }
            '*' { respond beehive, [status: CREATED] }
        }
    }

    def edit(Beehive beehive) {
        respond beehive
    }

    @Transactional
    def update(Beehive beehive) {
        if (beehive == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (beehive.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond beehive.errors, view:'edit'
            return
        }

        beehive.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'beehive.label', default: 'Beehive'), beehive.id])
                redirect beehive
            }
            '*'{ respond beehive, [status: OK] }
        }
    }

    @Transactional
    def delete(Beehive beehive) {

        if (beehive == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        beehive.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'beehive.label', default: 'Beehive'), beehive.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'beehive.label', default: 'Beehive'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

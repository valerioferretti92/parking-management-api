package com.valerioferretti.parking.repository.impl;

import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.repository.InvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDaoImpl implements InvoiceDao {

    private MongoTemplate mongoTemplate;

    @Autowired
    public InvoiceDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Invoice insert(Invoice invoice) {
        mongoTemplate.insert(invoice);
        return invoice;
    }

    public List<Invoice> findAll() {
        return mongoTemplate.findAll(Invoice.class);
    }
}

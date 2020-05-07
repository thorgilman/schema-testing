package com.template.webserver;

import com.template.flows.IssueFlow;
import com.template.objects.Beef;
import com.template.objects.GenericProduct;
import com.template.objects.Produce;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.transactions.SignedTransaction;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/") // The paths for HTTP requests are relative to this base path.
public class Controller {
    private final CordaRPCOps proxy;
    private final static Logger logger = LoggerFactory.getLogger(Controller.class);

    public Controller(NodeRPCConnection rpc) {
        this.proxy = rpc.proxy;
    }

    @PostMapping(value = "/produce", produces = "text/plain")
    private String issue() throws ParseException {
        try{
            GenericProduct produce = new Produce("Carrot", "Orange");
            SignedTransaction stx = proxy.startFlowDynamic(IssueFlow.InitiatorFlow.class, produce).getReturnValue().get();
        }
        catch(Exception flowException){
            flowException.printStackTrace();
        }
        return "";
    }

    @PostMapping(value = "/beef", produces = "text/plain")
    private String beef() throws ParseException {
        try{
            GenericProduct beef = new Beef("Austrailian", "10.5");
            SignedTransaction stx = proxy.startFlowDynamic(IssueFlow.InitiatorFlow.class, beef).getReturnValue().get();
        }
        catch(Exception flowException){
            flowException.printStackTrace();
        }
        return "";
    }

}
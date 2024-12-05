package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import picocli.CommandLine;
import java.io.IOException;

@CommandLine.Command(description = "This is Azkivam CLI", name = "",
        mixinStandardHelpOptions = true, version = "aki 1.0", subcommands = {})
public class AzkivamUi {

    private static String CREATE_ACCOUNT_URI = "http://127.0.0.1:8080/accounts";
    private static String RETRIEVE_ACCOUNT_URI = "http://127.0.0.1:8080/accounts";
    private static String WITHDRAW_URI = "http://127.0.0.1:8080/accounts";
    private static String DEPOSIT_URI = "http://127.0.0.1:8080/accounts";
    private static String TRANSFER_URI = "http://127.0.0.1:8080/accounts/transfer";

    @CommandLine.Command(name = "createAccount", description = "Defines a new Account")
    public void createAccount (
            @CommandLine.Option(names = {"-n", "--accountNumber"}, required = true) String accountNumber,
            @CommandLine.Option(names = {"-h", "--holderName"}, required = true) String holderName){
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(CREATE_ACCOUNT_URI);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            ObjectMapper mapper = new ObjectMapper();
            String jsonInput = mapper.writeValueAsString(new CreateAccountForm(accountNumber, holderName));
            StringEntity stringEntity = new StringEntity(jsonInput);
            httpPost.setEntity(stringEntity);
            System.out.println(httpclient.execute(httpPost,responseHandler));
        } catch (IOException e) {
            System.out.println("error in io");
        }
    }


    @CommandLine.Command(name = "getAccountInfo", description = "Retrieve an account")
    public void retrieveAccount (
            @CommandLine.Option(names = {"-i", "--accountId"}, required = true) Long accountId){
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(String.format(RETRIEVE_ACCOUNT_URI+"/%s", accountId));
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            System.out.println(httpclient.execute(httpGet,responseHandler));
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    @CommandLine.Command(name = "withdraw", description = "Withdraw a specified amount from the account balance")
    public void withdraw (@CommandLine.Option(names = {"-i", "--accountId"}, required = true) Long accountId,
                          @CommandLine.Option(names = {"-a", "--amount"}, description = "the amount to withdraw", required = true)double amount) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String uri = String.format(WITHDRAW_URI + "/%s/withdraw?amount=IRR%s", accountId, amount);
            HttpPatch httpPatch = new HttpPatch(uri);
            httpPatch.setHeader("Accept", "application/json");
            httpPatch.setHeader("Content-type", "application/json");
            System.out.println(httpclient.execute(httpPatch, responseHandler));
        } catch (IOException e) {
            System.out.println("error in io");
        }
    }


    @CommandLine.Command(name = "deposit", description = "Adds funds to the account balance")
    public void deposit (@CommandLine.Option(names = {"-i", "--accountId"}, required = true) Long accountId,
                          @CommandLine.Option(names = {"-a", "--amount"}, description = "the amount to deposit", required = true)double amount) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String uri = String.format(DEPOSIT_URI + "/%s/deposit?amount=IRR%s", accountId, amount);
            HttpPatch httpPatch = new HttpPatch(uri);
            httpPatch.setHeader("Accept", "application/json");
            httpPatch.setHeader("Content-type", "application/json");
            System.out.println(httpclient.execute(httpPatch, responseHandler));
        } catch (IOException e) {
            System.out.println("error in io");
        }
    }

    @CommandLine.Command(name = "transfer", description = "Moves funds from one account to another")
    public void transfer (@CommandLine.Option(names = {"-s", "--sourceAccountNumber"}, description = "source account number", required = true) String srcAccountNumber,
                         @CommandLine.Option(names = {"-d", "--destinationAccountNumber"}, description = "destination account number", required = true) String destAccountNumber,
                          @CommandLine.Option(names = {"-a", "--amount"}, description = "the amount to deposit", required = true) double amount) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            MoneyTransferForm moneyTransferForm = new MoneyTransferForm(srcAccountNumber, destAccountNumber, "IRR" + amount);
            HttpPatch httpPatch = new HttpPatch(TRANSFER_URI);
            httpPatch.setHeader("Accept", "application/json");
            httpPatch.setHeader("Content-type", "application/json");
            ObjectMapper mapper = new ObjectMapper();
            String jsonInput = mapper.writeValueAsString(moneyTransferForm);
            StringEntity stringEntity = new StringEntity(jsonInput);
            httpPatch.setEntity(stringEntity);
            System.out.println(httpclient.execute(httpPatch, responseHandler));
        } catch (IOException e) {
            System.out.println("error in io");
        }
    }



    private static ResponseHandler<String> responseHandler = response -> {
        int status = response.getStatusLine().getStatusCode();
        if(status == 409){
            System.out.println(EntityUtils.toString(response.getEntity()));
        }else if(status == 400){
            System.out.println("You may have entered some incorrect input parameters");
        }else if(status == 500){
            System.out.println("Server error");
        }else if(status >= 200 && status < 300){
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        }else {
            throw new HttpResponseException(status, "Unexpected response status: " + status);
        }
        return "";
    };

    public static void main(String[] args) {
        int exitCode = new CommandLine(new AzkivamUi()).execute(args);
        System.exit(exitCode);
    }
}
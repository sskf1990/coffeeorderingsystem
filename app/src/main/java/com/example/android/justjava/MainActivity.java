package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.v("MainActivity", "Name: " + name);


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        Log.v("MainActivity", "Has Whipped Cream: " + hasWhippedCream);
        Log.v("MainActivity", "Has Chocolate: " + hasChocolate);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
    /**
     * Calculates the price of the order.
     *@return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if(addWhippedCream){
            basePrice = basePrice + 1;
        }

        if(addChocolate){
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }
    /**
     * Create summary of the order.
     *
     * @param name of the customer
     * @param price of the order
     * @param addWippedCream is whether or not the user wants whipped cream topping
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage = priceMessage + "\nAdd Whipped Cream? " + addWippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    public void incrementQuantity(View view) {
        if(quantity == 100){
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing left to do.
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrementQuantity(View view) {
        if(quantity == 1){

            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();

            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}
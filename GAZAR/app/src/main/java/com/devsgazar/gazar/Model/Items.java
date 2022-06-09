package com.devsgazar.gazar.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

public class Items {
    private String ItemID, ItemTitle, ItemDescription, ItemPrice;
    private ArrayList<Uri> ItemImages;

    public Items(String itemID, String itemTitle, String itemDescription, String itemPrice, ArrayList<Uri> itemImages) {
        ItemID = itemID;
        ItemTitle = itemTitle;
        ItemDescription = itemDescription;
        ItemPrice = itemPrice;
        ItemImages = itemImages;
    }

    public Items() {
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getItemTitle() {
        return ItemTitle;
    }

    public void setItemTitle(String itemTitle) {
        ItemTitle = itemTitle;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public ArrayList<Uri> getItemImages() {
        return ItemImages;
    }

    public void setItemImages(ArrayList<Uri> itemImages) {
        ItemImages = itemImages;
    }
}

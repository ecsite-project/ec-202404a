package com.example.form;

/**
 * 注文確認情報の入力フォーム.
 *
 * @author rui.inoue
 */
public class OrderForm {
    /** 注文のid */
    private Integer orderId;
    /** 名前 */
    private String destinationName;
    /** メールアドレス */
    private String destinationEmail;
    /** 郵便番号 */
    private String destinationZipcode;
    /** 都道府県 */
    private String destinationPrefecture;
    /** 市区町村 */
    private String destinationMunicipalities;
    /** 住所 */
    private String destinationAddress;
    /** 電話番号 */
    private String destinationTel;
    /** 配達日 */
    private String deliveryDate;
    /** 配達時間 */
    private String deliveryTime;
    /** 支払い方法 */
    private Integer paymentMethod;
    /** カード番号 */
    private Integer cardNumber;
    /** カード有効期限（年） */
    private Integer cardExpYear;
    /** カード有効期限（月） */
    private Integer cardExpMonth;
    /** カード名義人 */
    private String cardName;
    /** セキュリティコード */
    private Integer cardCvv;

    @Override
    public String toString() {
        return "OrderForm{" +
                "orderId=" + orderId +
                ", destinationName='" + destinationName + '\'' +
                ", destinationEmail='" + destinationEmail + '\'' +
                ", destinationZipcode='" + destinationZipcode + '\'' +
                ", destinationPrefecture='" + destinationPrefecture + '\'' +
                ", destinationMunicipalities='" + destinationMunicipalities + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", destinationTel='" + destinationTel + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", cardNumber=" + cardNumber +
                ", cardExpYear=" + cardExpYear +
                ", cardExpMonth=" + cardExpMonth +
                ", cardName='" + cardName + '\'' +
                ", cardCvv=" + cardCvv +
                '}';
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationEmail() {
        return destinationEmail;
    }

    public void setDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
    }

    public String getDestinationZipcode() {
        return destinationZipcode;
    }

    public void setDestinationZipcode(String destinationZipcode) {
        this.destinationZipcode = destinationZipcode;
    }

    public String getDestinationPrefecture() {
        return destinationPrefecture;
    }

    public void setDestinationPrefecture(String destinationPrefecture) {
        this.destinationPrefecture = destinationPrefecture;
    }

    public String getDestinationMunicipalities() {
        return destinationMunicipalities;
    }

    public void setDestinationMunicipalities(String destinationMunicipalities) {
        this.destinationMunicipalities = destinationMunicipalities;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getDestinationTel() {
        return destinationTel;
    }

    public void setDestinationTel(String destinationTel) {
        this.destinationTel = destinationTel;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCardExpYear() {
        return cardExpYear;
    }

    public void setCardExpYear(Integer cardExpYear) {
        this.cardExpYear = cardExpYear;
    }

    public Integer getCardExpMonth() {
        return cardExpMonth;
    }

    public void setCardExpMonth(Integer cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Integer getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(Integer cardCvv) {
        this.cardCvv = cardCvv;
    }
}

    package com.TJokordeGdeAgungAbelPutraJBusER;
    import java.sql.Timestamp;

    /**
     * Class Invoce merepresentasikan invoice dari pembayaran
     *
     *
     * @author Tjokorde Gde Agung Abel Putra
     * @version 1.0
     */
    public class Invoice extends Serializable{
        /** Waktu dari transaksi */
        public Timestamp time;
        /** ID akun pembeli */
        public int buyerId;
        /** ID akun renter */
        public int renterId;
        /** Rating dari Bus */
        public BusRating rating;
        /** Status Pembayaran */
        public PaymentStatus status;

        /**
         * Enum merepresentasikan status dari pembayaran
         */
        public enum PaymentStatus{
            /**
             * Pemabayaran Gagal/ dibatalkan
             */
            FAILED,
            /**
             * Pembayaran sedang menunggu disetujui
             */
            WAITING,
            /**
             * Pembayaran Berhasil
             */
            SUCCESS;
        }

        /**
         * Enum merepresentasikan rating dari bus
         */
        public enum BusRating{
            /**
             * Tidak ada rating
             */
            NONE,
            /**
             * Rating biasa saja
             */
            NEUTRAL,
            /**
             * Rating Bagus
             */
            GOOD,
            /**
             * Rating burul
             */
            BAD;
        }
        /**
         * Protected Constructor untuk membuat Invoice secara internal
         *
         * @param buyerId  ID Akun pembeli
         * @param renterId ID Akun dari Renter
         * @param time   Waktu transaksi
         */
        protected Invoice(int buyerId, int renterId, Timestamp time){
            super();
            this.buyerId = buyerId;
            this.renterId = renterId;
            this.rating = BusRating.NONE;
            this.status = PaymentStatus.WAITING;
            this.time = time;
        }
        /**
         * Constructor untuk membuat Invoice
         *
         * @param buyer  Akun pembeli
         * @param renter Akun dari Renter
         * @param time   Waktu transaksi
         */
        public Invoice(Account buyer, Renter renter, Timestamp time){
            super();
            this.buyerId = buyer.id;
            this.renterId = renter.id;
            this.rating = BusRating.NONE;
            this.status = PaymentStatus.WAITING;
            this.time = time;
        }
    }

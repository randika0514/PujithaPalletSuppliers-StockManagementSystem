package model;

public class Supplier {
        private String SupId;
        private String SupName;
        private String SupAddress;
        private String SupContact;

        public Supplier() {
        }

        public Supplier(String supId, String supName, String supAddress, String supContact) {
                SupId = supId;
                SupName = supName;
                SupAddress = supAddress;
                SupContact = supContact;
        }

        public String getSupId() {
                return SupId;
        }

        public void setSupId(String supId) {
                SupId = supId;
        }

        public String getSupName() {
                return SupName;
        }

        public void setSupName(String supName) {
                SupName = supName;
        }

        public String getSupAddress() {
                return SupAddress;
        }

        public void setSupAddress(String supAddress) {
                SupAddress = supAddress;
        }

        public String getSupContact() {
                return SupContact;
        }

        public void setSupContact(String supContact) {
                SupContact = supContact;
        }

        @Override
        public String toString() {
                return "Supplier{" +
                        "SupId='" + SupId + '\'' +
                        ", SupName='" + SupName + '\'' +
                        ", SupAddress='" + SupAddress + '\'' +
                        ", SupContact='" + SupContact + '\'' +
                        '}';
        }
}

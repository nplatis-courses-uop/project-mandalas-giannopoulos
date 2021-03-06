## Στοιχεία φοιτητών

 1. **Ονοματεπώνυμο:** Στέφανος Μανδαλάς

    **ΑΜ:** 2022201700107

 2. **Ονοματεπώνυμο:** Γιαννόπουλος Κωνσταντίνος

    **ΑΜ:** 2022201700033

---

## Javadoc
Οι διάρθρωση των κλάσεων, καθώς και τα `public` πεδία και οι `public` μέθοδοί τους, τεκμηριώνονται με javadoc σχόλια.

## Maven multi-module project
Με κίνητρο τη χρήση κοινής κλάσης ανάμεσα στις δύο διαφορετικές εφαρμογές server/client, χρησιμοποιείται η λειτουργεία *multi-module project* του maven.

Το project διαθέτει 3 module: Server, Client, Common. Υπάρχει ένα εξωτερικό `pom.xml` το οποίο ρυθμίζει το `groupId` (ως `gr.uop`) και το `version`, και το οποίο συναθροίζει τα module. Κάθε module διαθέτει ένα ξεχωριστό `pom.xml` όπου και αναγράφεται το `gr.uop` ως `parent`. Έτσι, υπάρχει η δυνατότητα να οριστεί το module `Common` ως εξάρτηση στα module `Server` και `Client`.

## Multithreading
Υλοποιήθηκε η `Runnable` κλάση `OrderServer` που ακούει για συνδέσεις από τον client που στέλνει παραγγελίες. Η `OrderServer` εκκινείται σε ξεχωριστό thread κατά την εκκίνηση της εφαρμογής. Υλοποιήθηκε επίσης η `Runnable` κλάση `OrderProcessor`, η οποία εκκινείται από την `OrderServer` κάθε φορά που αυτή κάνει μία σύνδεση και λαμβάνει μία παραγγελία.

## SQLite & ORMLite
Το βιβλίο εσόδων υλοποιήθηκε ως βάση δεδομένων SQLite και βρίσκεται στο αρχείο `Server/db/book.db`. Αντί για χειρόγραφα SQL query, ο χειρισμός της βάσης γίνεται με την τεχνολογία ORM, με χρήση της βιβλιοθήκης ORMLite.

*Οι βάσεις δεδομένων SQLite είναι ενσωματωμένες στην εφαρμογή, και έτσι δεν απαιτείται επικοινωνία με κάποια εξωτερική διεργασία.*

## CSS
Το UI των εφαρμογών μορφοποιείται με CSS.
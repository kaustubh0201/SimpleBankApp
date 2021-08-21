package com.example.bankapp

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.bankapp.bank.data.Transaction
import com.example.bankapp.bank.data.User
import com.example.bankapp.bank.data.ui.TransactionViewModel
import com.example.bankapp.bank.data.ui.UserViewModel
import com.example.bankapp.ui.theme.BankAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val userViewModel : UserViewModel by viewModels()
    private val transactionViewModel : TransactionViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color(0xFF202020), modifier = Modifier.fillMaxSize()) {
                    Navigation()
                }
            }
        }
    }

    private fun update(id: MutableState<String>, amount: MutableState<String>){
        lifecycleScope.launchWhenStarted {
            if(!TextUtils.isEmpty(id.value) && !TextUtils.isEmpty(amount.value)){
                userViewModel.updateAmount(
                    amount.value,
                    id.value
                )
            }
        }
    }

    private fun delete(id: MutableState<String>, name: MutableState<String>, email: MutableState<String>, number: MutableState<String>, amount: MutableState<String>){
            lifecycleScope.launchWhenStarted {
                if(!TextUtils.isEmpty(name.value) && !TextUtils.isEmpty(email.value) && !TextUtils.isEmpty(id.value) && !TextUtils.isEmpty(number.value) && !TextUtils.isEmpty(amount.value)) {
                    userViewModel.delete(
                        User(
                            id.value,
                            name.value,
                            email.value,
                            number.value,
                            amount.value
                        )
                    )
                }else{
                    Toast.makeText(this@MainActivity, "Error in Transaction", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun insert(id: MutableState<String>, name: MutableState<String>, email: MutableState<String>, number: MutableState<String>, amount: MutableState<String>){
        lifecycleScope.launchWhenStarted {
            if(!TextUtils.isEmpty(name.value) && !TextUtils.isEmpty(email.value) && !TextUtils.isEmpty(id.value) && !TextUtils.isEmpty(number.value) && !TextUtils.isEmpty(amount.value)){
                userViewModel.insert(
                    User(
                        id.value,
                        name.value,
                        email.value,
                        number.value,
                        amount.value
                    )
                )
                Toast.makeText(this@MainActivity, "Inserted...", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@MainActivity, "Empty...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertTransaction(toID: MutableState<String>, fromID: MutableState<String>, amount: MutableState<String>){
        lifecycleScope.launchWhenStarted {
            if(!TextUtils.isEmpty(toID.value) && !TextUtils.isEmpty(fromID.value)) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formatted = current.format(formatter)
                transactionViewModel.insert(
                    Transaction(
                        toID.value,
                        fromID.value,
                        formatted,
                        amount.value
                    )
                )
                Toast.makeText(this@MainActivity, "Transaction Done Successfully", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@MainActivity, "Transaction Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun TransferProcess(id: String, name: String, email: String, number: String, amount: String, navController: NavController) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "TRANSACTION", textAlign = TextAlign.Center)
                    },
                    backgroundColor = Color(0xffff781f)
                )
            }
        ) {
            val idNew = remember {
                mutableStateOf(id)
            }
            val nameNew = remember {
                mutableStateOf(name)
            }
            val emailNew = remember {
                mutableStateOf(email)
            }
            val numberNew = remember {
                mutableStateOf(number)
            }
            val amountNew = remember {
                mutableStateOf(amount)
            }

            val ID = remember {
                mutableStateOf(id)
            }
            Column(
                modifier = Modifier.padding(
                    top = 40.dp,
                    start = 40.dp
                )
            ) {
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = "Account Holder's ID: ",
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.h1,
                        fontSize = 20.sp
                    )
                    Text(
                        text = id,
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.body1,
                        fontSize = 20.sp
                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = "Account Holder's Name: ",
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.h1,
                        fontSize = 20.sp
                    )
                }
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = name,
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.body1,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = "Account Holder's Email: ",
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.h1,
                        fontSize = 20.sp
                    )
                }
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = email,
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.body1,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = "Account Holder's Contact: ",
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.h1,
                        fontSize = 20.sp
                    )
                }
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = number,
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.body1,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = "Account Balance: ",
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.h1,
                        fontSize = 20.sp
                    )
                    Text(
                        text = amount,
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.body1,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Text(
                        text = "Transfer To: ",
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.h1,
                        fontSize = 20.sp
                    )
                }
                val toID = remember {
                    mutableStateOf("")
                }
                Row {

                    TextField(
                        value = toID.value,
                        onValueChange = {
                            toID.value = it
                        },
                        placeholder = {
                            Text(text = "Enter ID of Receiver")
                        },
                        modifier = Modifier.padding(10.dp)

                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Transfer Amount: ",
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.h1,
                        fontSize = 20.sp
                    )
                }
                val toAmount = remember {
                    mutableStateOf("")
                }
                Row {

                    TextField(
                        value = toAmount.value,
                        onValueChange = {
                            toAmount.value = it
                        },
                        placeholder = {
                            Text(text = "Enter Amount") },
                        modifier = Modifier.padding(10.dp)

                    )
                }
                Spacer(modifier = Modifier.height(10.dp))



                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Button(onClick = {
                        //delete(idNew, nameNew, emailNew, numberNew, amountNew)
                        val a = toAmount.value
                        val remainingAmount = (amount.toInt() - a.toInt())
                        amountNew.value = remainingAmount.toString()
                        if(remainingAmount < 0){
                            amountNew.value = amount
                            Toast.makeText(this@MainActivity, "Transaction Unsuccessful", Toast.LENGTH_SHORT).show()
                        }else{
                            insertTransaction(ID, toID, toAmount)
                            update(toID, toAmount)
                        }
                        update(idNew, amountNew)


                        //insert(idNew, nameNew, emailNew, numberNew, amountNew)

                        navController.navigate("customerView")
                    },
                    ) {
                        Text(text = "Transfer Amount")
                    }
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "splash_screen") {
            composable("splash_screen") {
                SplashScreen(navController = navController)
            }

            composable("customerView"){
                AddToolbar(navController = navController)
            }

            composable("main_screen") {
                MainView(navController = navController)
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(text = "MAIN_SCREEN", color = Color.White)
//            }
            }

            composable("transactionView") {
                TransactionView(navController = navController)
            }

            composable(
                "transactionDetail/{id}/{toID}/{fromID}/{date}",
                arguments = listOf(
                    navArgument("id"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    },
                    navArgument("toID"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    },
                    navArgument("fromID"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    },
                    navArgument("date"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    }
                )
            ) {

            }

            composable(
                "transaction/{id}/{name}/{email}/{number}/{amount}",
                arguments = listOf(
                    navArgument("name"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    },
                    navArgument("id"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    },
                    navArgument("email"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    },
                    navArgument("number"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    },
                    navArgument("amount"){
                        type = NavType.StringType
                        defaultValue = "Unknown"
                    }
                )
            ) { entry ->
                entry.arguments?.getString("id")?.let {
                    TransferProcess(
                        id = it,
                        name = entry.arguments?.getString("name")!!,
                        email = entry.arguments?.getString("email")!!,
                        number = entry.arguments?.getString("number")!!,
                        amount = entry.arguments?.getString("amount")!!,
                        navController = navController
                    )
                }
            }




        }

    }

// Splash Screen Functions

    @Composable
    fun SplashScreen(navController: NavController) {
        val scale = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = true){
            scale.animateTo(
                targetValue = 0.3f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = {
                        OvershootInterpolator(2f).getInterpolation(it)
                    }
                )
            )
            delay(3000L)
            navController.navigate("main_screen")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bank_svgrepo_com),
                        contentDescription = "logo",
                        modifier = Modifier.scale(scale.value)
                    )
                    Text(
                        "DISNEY BANK",
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFDF4DC),
                        style = MaterialTheme.typography.h1,
                        fontSize = 30.sp
                    )
                }

            }
        }

    }


    @Composable
    fun TransactionView(navController: NavController){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "DISNEY BANK")
                    }
                )
            }
        ){
            RecyclerTransactionView(transactionViewModel = transactionViewModel, navController = navController)
        }
    }
// Main_Screen Functions

    @Composable
    fun AddToolbar(navController: NavController) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "ACCOUNT HOLDERS")
                    }
                )
            },
            floatingActionButton = {
                val openDialog = remember {
                    mutableStateOf(false)
                }
                FloatingActionButton(onClick = {
                    openDialog.value = true
                }) {
                    AddDialogBox(openDialog = openDialog)
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) {
            Recyclerview(userViewModel = userViewModel, navController = navController)
        }
    }

    @Composable
    fun MainView(navController: NavController){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "DISNEY BANK", textAlign = TextAlign.Center)
                    },
                    backgroundColor = Color(0xffff781f)
                )
            }
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 150.dp, start = 100.dp)
            ){
                Row {
                    Button(onClick = {
                        navController.navigate("customerView")
                    },
                    ) {
                        Text(text = "View All Customers")
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Button(onClick = {
                        navController.navigate("transactionView")
                    },
                    ) {
                        Text(text = "View All Transactions")
                    }
                }
            }
        }
    }

    @Composable
    fun AddDialogBox(openDialog: MutableState<Boolean>) {
        val id = remember {
            mutableStateOf("")
        }
        val name = remember {
            mutableStateOf("")
        }
        val email = remember {
            mutableStateOf("")
        }
        val number = remember {
            mutableStateOf("")
        }
        val amount = remember {
            mutableStateOf("")
        }

        if(openDialog.value){

            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Customer Information", modifier = Modifier.padding(bottom = 25.dp))
                },
                text = {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = id.value,
                            onValueChange = {
                                id.value = it
                            },
                            placeholder = {
                                Text(text = "Enter ID")
                            },
                            label = {
                                Text(text = "Enter ID")
                            },
                            modifier = Modifier.padding(10.dp)
                        )
                        OutlinedTextField(
                            value = name.value,
                            onValueChange = {
                                name.value = it
                            },
                            placeholder = {
                                Text(text = "Enter Name")
                            },
                            label = {
                                Text(text = "Enter Name")
                            },
                            modifier = Modifier.padding(10.dp)
                        )
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = {
                                email.value = it
                            },
                            placeholder = {
                                Text(text = "Enter Email")
                            },
                            label = {
                                Text(text = "Enter Email")
                            },
                            modifier = Modifier.padding(10.dp)
                        )
                        OutlinedTextField(
                            value = number.value,
                            onValueChange = {
                                number.value = it
                            },
                            placeholder = {
                                Text(text = "Enter Mobile Number")
                            },
                            label = {
                                Text(text = "Enter Mobile Number")
                            },
                            modifier = Modifier.padding(10.dp)
                        )
                        OutlinedTextField(
                            value = amount.value,
                            onValueChange = {
                                amount.value = it
                            },
                            placeholder = {
                                Text(text = "Enter Amount")
                            },
                            label = {
                                Text(text = "Enter Amount")
                            },
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                },
                confirmButton = {
                    OutlinedButton(onClick = {
                        insert(id, name, email, number, amount)
                        openDialog.value = false
                    }) {
                        Text(text = "Save")
                    }
                }
            )
        }
    }

    @Composable
    fun EachRow(user: User, navController: NavController){
        val id = user.id
        val name = user.name
        val email = user.email
        val number = user.number
        val amount = user.currentBalance
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .clickable { navController.navigate("transaction/$id/$name/$email/$number/$amount") },
            elevation = 2.dp,
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                Text(text = user.name, fontWeight = FontWeight.ExtraBold)
                Text(text = user.email, fontStyle = FontStyle.Italic)
            }
        }
    }

    @Composable
    fun EachTransactionRow(transaction: Transaction, navController: NavController){
        val txnID = transaction.txnID
        val toID = transaction.toID
        val fromID = transaction.fromID
        val date = transaction.date
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .clickable { navController.navigate("transactionDetail/$txnID/$toID/$fromID/$date") },
            elevation = 2.dp,
            shape = RoundedCornerShape(4.dp)
        ){
            Column(
                modifier  = Modifier.padding(10.dp),
            ) {
                Text(text = txnID.toString(), fontWeight = FontWeight.ExtraBold)
                Text(text = toID, fontWeight = FontWeight.Light)
                Text(text = fromID, fontWeight = FontWeight.Light)
            }
        }
    }

    @Composable
    fun RecyclerTransactionView(transactionViewModel: TransactionViewModel, navController: NavController){
        LazyColumn{
            items(transactionViewModel.response.value){ transaction ->
                EachTransactionRow(transaction = transaction, navController = navController)
            }
        }
    }
    
    @Composable
    fun Recyclerview(userViewModel: UserViewModel, navController: NavController) {
        LazyColumn{
            items(userViewModel.response.value){ user ->
                EachRow(user = user, navController = navController)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        BankAppTheme {
        }
    }

}


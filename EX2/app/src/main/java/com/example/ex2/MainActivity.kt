package com.example.ex2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ex2.ui.theme.EX2Theme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EX2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Ex2Layout()
                }
            }
        }
    }
}
@Composable
fun Ex2Layout(){
    var applyTaxe by remember {
        mutableStateOf(false)
    }
    var priceInput by remember {
        mutableStateOf("")
    }
    var quantityInput by remember {
        mutableStateOf("")
    }
    val price=priceInput.toDoubleOrNull()?:0.0
    val quantity=quantityInput.toIntOrNull()?:0
    val total= calculateTotalAmount(price,quantity,applyTaxe)
    Column (modifier= Modifier
        .statusBarsPadding()
        .padding(horizontal = 40.dp)
        .verticalScroll(rememberScrollState())
        .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = stringResource(R.string.Calculate_price),
            modifier= Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
            )
        NumberField(label =R.string.prix_unitaire ,
            keyboardOptions =KeyboardOptions.Default.copy(
                keyboardType=KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = priceInput,
            onValueChanged = {priceInput=it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        NumberField(label =R.string.quantite_produits ,
            keyboardOptions =KeyboardOptions.Default.copy(
                keyboardType=KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = quantityInput,
            onValueChanged = {quantityInput=it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        ApplyTaxeInput(applyTaxe = applyTaxe, onApplyTaxeChange = {applyTaxe=it},
            modifier = Modifier.padding(bottom = 32.dp))
        Text(text = stringResource(R.string.Total,total),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
    }








@Composable
fun NumberField(
                @StringRes label:Int,
                keyboardOptions:KeyboardOptions,
                value:String,
                onValueChanged:(String)->Unit,
                modifier: Modifier){
    TextField(value = value,
        onValueChange =onValueChanged,
        modifier = modifier,
        singleLine = true,
        label={Text(stringResource(label))},
        keyboardOptions=keyboardOptions
        )

}
@Composable
fun ApplyTaxeInput(modifier: Modifier= Modifier,applyTaxe:Boolean,onApplyTaxeChange:(Boolean)->Unit){
    Row (
        modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = stringResource(R.string.Apply_Taxe))
        Switch(checked = applyTaxe, onCheckedChange = onApplyTaxeChange,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End))

    }

}



private fun calculateTotalAmount(price:Double,quantity:Int,applyTaxe:Boolean):String{
    var total=price*quantity
    if(applyTaxe){
        total*=1.2
    }
    return NumberFormat.getCurrencyInstance().format(total)
}
@Preview(showBackground = true)
@Composable
fun EX2Preview() {
    EX2Theme {
        Ex2Layout()
    }
}
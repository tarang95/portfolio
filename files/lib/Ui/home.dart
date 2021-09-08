import 'package:files/Model/Question.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class QuizApp extends StatefulWidget {
  const QuizApp({Key? key}) : super(key: key);

  @override
  _QuizAppState createState() => _QuizAppState();
}

class _QuizAppState extends State<QuizApp> {
  int _index=0;
  List _questions = [
    Question.name("Prince Harry is taller than Prince William", false),
    Question.name("The star sign Aquarius is represented by a tiger", true),
    Question.name("Meryl Streep has won two Academy Awards", false),
    Question.name("Marrakesh is the capital of Morocco", false),
    Question.name("Idina Menzel sings 'let it go' 20 times in 'Let It Go' from Frozen", false)
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Quiz App"),
        centerTitle: true,
        backgroundColor: Colors.blueGrey,
      ),
      backgroundColor: Colors.blueGrey,
      body: Container(
        child: Column(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(
                  top: MediaQuery.of(context).size.height * 0.05),
              child: Center(
                  child: Image.asset(
                "images/flag_au.png",
                height: 180,
                width: 250,
              )),
            ),
            Container(
              padding: EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: Colors.transparent,
                borderRadius: BorderRadius.circular(18),
                border: Border.all(
                  color: Colors.white30,
                )
              ),
              margin: EdgeInsets.only(
                  top: MediaQuery.of(context).size.height * 0.05),
              height: 120,
              width: MediaQuery.of(context).size.width*0.9,
              child: Center(
                child:
                Text(
                  _questions[_index].quetionTag,
                  style: TextStyle(fontSize: 19, color: Colors.white),
                ),
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: <Widget>[
                ElevatedButton(onPressed: ()=>_previousQuestion(),child: Icon(Icons.arrow_back),style: ButtonStyle(backgroundColor: MaterialStateProperty.all(Colors.white10)),),
                ElevatedButton(onPressed: ()=>_checkAnswer(true),child: Text("TRUE"),style: ButtonStyle(backgroundColor: MaterialStateProperty.all(Colors.white10)),),
                ElevatedButton(onPressed: ()=>_checkAnswer(false),child: Text("FALSE"),style: ButtonStyle(backgroundColor: MaterialStateProperty.all(Colors.white10)),),
                ElevatedButton(onPressed: ()=>_nextQuestion(),child: Icon(Icons.arrow_forward),style: ButtonStyle(backgroundColor: MaterialStateProperty.all(Colors.white10)),)
              ],
            )
          ],
        ),
      ),
    );
  }

  _checkAnswer(bool _userchoice) {
    if(_userchoice == _questions[_index].isCorrect){
      ScaffoldMessenger.of(context).removeCurrentSnackBar();
      debugPrint("Correct");
      final snackBar=SnackBar(content: Text("Correct",style: TextStyle(
        fontSize: 18,
      ),),backgroundColor: Colors.green,);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      _updateQuestion();
    }
    else{
      ScaffoldMessenger.of(context).removeCurrentSnackBar();
      debugPrint("Incorrect");
      final snackBar=SnackBar(content: Text("Incorrect",
        style: TextStyle(
        fontSize: 18,
      ),),
        backgroundColor: Colors.redAccent,);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      _updateQuestion();
    }
  }
  _updateQuestion(){
    setState(() {
      _index=(_index+1)%_questions.length;
    });
  }

  _nextQuestion() {
    _updateQuestion();
  }

  _previousQuestion() {
    setState(() {
      _index=(_index-1)%_questions.length;
    });
  }
}

class ScaffoldEx extends StatelessWidget {
  int i = 0;

  _tapButton() {
    i++;
    debugPrint("Tapped wifi button" + i.toString());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold();
  }
}

class TipCalculator extends StatefulWidget {
  @override
  _TipCalculatorState createState() => _TipCalculatorState();
}

class _TipCalculatorState extends State<TipCalculator> {
  int _tipPercent = 0;
  double _totalBill = 0.0;
  int _splitValue = 1;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        margin: EdgeInsets.only(top: MediaQuery.of(context).size.height * 0.15),
        alignment: Alignment.center,
        child: ListView(
          scrollDirection: Axis.vertical,
          padding: EdgeInsets.all(20.5),
          children: <Widget>[
            Container(
              width: 200,
              height: 175,
              decoration: BoxDecoration(
                  color: Colors.deepPurple.shade100,
                  borderRadius: BorderRadius.circular(25)),
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    Text(
                      "Total per Person",
                      style: TextStyle(
                        color: Colors.deepPurple,
                        fontSize: 20,
                        fontWeight: FontWeight.w500,
                      ),
                    ),
                    Text(
                      "\$ ${calculateTotalPerPerson(_totalBill, _splitValue, _tipPercent)}",
                      style: TextStyle(
                        color: Colors.deepPurple,
                        fontSize: 28,
                        fontWeight: FontWeight.w700,
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Container(
              margin: EdgeInsets.only(top: 15),
              padding: EdgeInsets.all(20.5),
              decoration: BoxDecoration(
                color: Colors.transparent,
                borderRadius: BorderRadius.circular(12),
                border: Border.all(width: 2, color: Colors.deepPurple.shade100),
              ),
              child: Column(
                children: <Widget>[
                  TextField(
                    keyboardType:
                        TextInputType.numberWithOptions(decimal: true),
                    style: TextStyle(color: Colors.deepPurple, fontSize: 20),
                    decoration: InputDecoration(
                      prefixText: "Amount: ",
                      prefixIcon: Icon(Icons.attach_money_sharp),
                    ),
                    onChanged: (String value) {
                      try {
                        if (double.parse(value) > 0.0)
                          _totalBill = double.parse(value);
                        else
                          _totalBill = 0.0;
                      } catch (exception) {
                        _totalBill = 0.0;
                      }
                    },
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(
                        "Split: ",
                        style: TextStyle(
                          color: Colors.deepPurple.shade500,
                          fontSize: 18,
                        ),
                      ),
                      Row(
                        children: <Widget>[
                          InkWell(
                            onTap: () {
                              setState(() {
                                if (_splitValue > 1) {
                                  _splitValue--;
                                } else {} //do nothing
                              });
                            },
                            child: Container(
                              width: 45,
                              height: 45,
                              margin: EdgeInsets.all(10),
                              decoration: BoxDecoration(
                                color: Colors.deepPurple.shade100,
                                borderRadius: BorderRadius.circular(15),
                              ),
                              child: Center(
                                child: Text(
                                  "-",
                                  style: TextStyle(
                                      color: Colors.black,
                                      fontWeight: FontWeight.bold,
                                      fontSize: 22),
                                ),
                              ),
                            ),
                          ), //- button
                          Text(
                            "$_splitValue",
                            style: TextStyle(
                                color: Colors.deepPurple,
                                fontSize: 20,
                                fontWeight: FontWeight.bold),
                          ),
                          InkWell(
                            onTap: () {
                              setState(() {
                                if (_splitValue > 0) {
                                  _splitValue++;
                                } else {} //do nothing
                              });
                            },
                            child: Container(
                              width: 45,
                              height: 45,
                              margin: EdgeInsets.all(10),
                              decoration: BoxDecoration(
                                color: Colors.deepPurple.shade100,
                                borderRadius: BorderRadius.circular(15),
                              ),
                              child: Center(
                                child: Text(
                                  "+",
                                  style: TextStyle(
                                      color: Colors.black,
                                      fontWeight: FontWeight.bold,
                                      fontSize: 22),
                                ),
                              ),
                            ),
                          ), //+ button
                        ],
                      ),
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(
                        "Tip:",
                        style: TextStyle(
                          color: Colors.deepPurple.shade500,
                          fontSize: 18,
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(14.0),
                        child: Text(
                          "\$ ${calculateTotalTip(_totalBill, _splitValue, _tipPercent)}",
                          style: TextStyle(
                            color: Colors.deepPurple,
                            fontSize: 22,
                          ),
                        ),
                      ),
                    ],
                  ),
                  Column(
                    children: <Widget>[
                      Text(
                        "$_tipPercent%",
                        style: TextStyle(
                          color: Colors.deepPurple.shade500,
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      Slider(
                          min: 0,
                          max: 50,
                          activeColor: Colors.deepPurple.shade500,
                          inactiveColor: Colors.deepPurple.shade100,
                          divisions: 10,
                          value: _tipPercent.toDouble(),
                          onChanged: (double newValue) {
                            setState(() {
                              _tipPercent = newValue.round();
                            });
                          })
                    ],
                  ), //slider
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  calculateTotalTip(double billAmount, int splitBy, int tipPercent) {
    double totalTip = 0.0;
    if (billAmount < 0 || billAmount.toString().isEmpty || billAmount == null) {
    } else {
      totalTip = (billAmount * tipPercent) / 100;
    }
    return totalTip;
  }

  calculateTotalPerPerson(double billAmount, int splitValue, int tipPercent) {
    double totalperPerson =
        (calculateTotalTip(billAmount, splitValue, tipPercent) + billAmount) /
            splitValue;
    return totalperPerson.toStringAsFixed(2);
  }
}

class Wisdom extends StatefulWidget {
  @override
  _WisdomState createState() => _WisdomState();
}

class _WisdomState extends State<Wisdom> {
  int _index = 0;
  List data = [
    "Australia",
    "India",
    "Germany",
    "Denmark",
    "USA",
    "Russia",
    "China"
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        alignment: Alignment.topCenter,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text("Traveling to:"),
            TextButton.icon(
              onPressed: _showRandomPic,
              icon: Icon(Icons.airplanemode_on),
              label: Text(data[_index % data.length]),
            ),
          ],
        ),
      ),
    );
  }

  void _showRandomPic() {
    setState(() {
      _index++;
    });
  }
}

class BizCard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Biz Card"),
      ),
      body: Container(
        alignment: Alignment.center,
        child: Stack(
          alignment: Alignment.topLeft,
          children: <Widget>[
            _getBizCard(),
            _getAvatar(),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.email_sharp),
        onPressed: () {},
      ),
    );
  }

  Container _getBizCard() {
    return Container(
      margin: EdgeInsets.fromLTRB(0, 35, 0, 0),
      width: 300,
      height: 180,
      alignment: Alignment.center,
      decoration: BoxDecoration(
        color: Colors.red.shade400,
        borderRadius: BorderRadius.circular(7),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Text(
            "Tarang Dave",
            style: TextStyle(
              fontSize: 24,
              color: Colors.white70,
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Icon(
                Icons.phone_android,
                color: Colors.indigo,
              ),
              Text(
                "Call: +61 451 214 700",
                style: TextStyle(
                  fontSize: 18,
                ),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Icon(
                Icons.email_sharp,
                color: Colors.indigo,
              ),
              Text(
                "admin@syventosolutions.com",
                style: TextStyle(
                  fontSize: 16,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Container _getAvatar() {
    return Container(
      margin: EdgeInsets.fromLTRB(20, 0, 0, 0),
      width: 80,
      height: 80,
      decoration: BoxDecoration(
        color: Colors.white10,
        borderRadius: BorderRadius.circular(50),
        border: Border.all(width: 1, color: Colors.lime),
        image: DecorationImage(
            image: NetworkImage("https://picsum.photos/200/300"),
            fit: BoxFit.cover),
      ),
    );
  }
}

class CustomButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('A SnackBar has been shown.'),
          ),
        );
      },
      child: Container(
        padding: EdgeInsets.all(8),
        decoration: BoxDecoration(
            color: Colors.deepPurpleAccent,
            borderRadius: BorderRadius.circular(3)),
        child: Text("Login"),
      ),
    );
  }
}

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Material(
      color: Colors.deepPurpleAccent,
      child: Center(
          child: Text(
        "Hello Flutter Dave",
        textDirection: TextDirection.ltr,
      )),
      textStyle: TextStyle(
        fontSize: 23.5,
        fontWeight: FontWeight.w500,
      ),
    );
  }
}

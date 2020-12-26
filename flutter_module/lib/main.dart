import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:logging/logging.dart';
import 'package:provider/provider.dart';
import 'package:flutter_module/service/cities_service.dart';
import 'widgets/checkable_city_listview.dart';
import 'model/checkable_city.dart';

void main() {
  _setupLogging();
  runApp(MyApp());
}

void _setupLogging() {
  Logger.root.level = Level.ALL;
  Logger.root.onRecord.listen((rec) {
    print('${rec.level.name}: ${rec.time}: ${rec.message}');
  });
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        primarySwatch: Colors.green,
      ),
      home: MyHomePage(title: 'Polular Cities'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> with WidgetsBindingObserver {
  static const platform = const MethodChannel('flutter_module/weather');
  AppLifecycleState _notification;
  List<CheckableCity> _cities = [];

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    print("app in $state");
    if (state == AppLifecycleState.resumed) {
      // Refresh UI when the engine is resumed
      setState(() {});
    }
  }

  @override
  initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  Future<void> _notifyError(context, msg) async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Oops'),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                Text('$msg'),
              ],
            ),
          ),
          actions: <Widget>[
            FlatButton(
              child: Text('OK'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  _navigateBack() {
    if (Navigator.canPop(context)) {
      Navigator.pop(context);
    } else {
      SystemNavigator.pop();
    }
  }

  Future<void> _setCityList(context, cities) async {
    try {
      await platform.invokeMethod('setCityList', cities);
      _navigateBack();
    } on PlatformException catch (e) {
      _notifyError(context, e.message);
    }
  }

  void _updateCityListToNative(context) {
    final selectedCities = _cities
        .where((element) => element.isChecked)
        .map((e) => e.city)
        .toList();
    print(selectedCities.toString());

    _setCityList(context, selectedCities);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Provider(
        create: (_) => CitiesService.create(),
        dispose: (_, CitiesService service) => service.client.dispose(),
        child: CheckableCityListView(_cities),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _updateCityListToNative(context),
        tooltip: 'Add',
        child: Icon(Icons.add),
      ),
    );
  }
}

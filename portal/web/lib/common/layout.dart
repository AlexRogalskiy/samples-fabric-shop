import 'package:flutter/material.dart';

class Layout extends StatelessWidget {
  final PreferredSizeWidget? appBar;
  final Widget? body;

  const Layout({super.key, this.appBar, this.body});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: appBar,
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: body,
        ));
  }
}

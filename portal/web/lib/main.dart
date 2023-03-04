import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';

import 'pages/public_home.dart';
import 'pages/sign_up.dart';
import 'common/user.dart';

void main() {
  runApp(App());
}

class App extends StatelessWidget {
  App({super.key});

  final GoRouter _routerConfig = GoRouter(
    routes: [
      GoRoute(
        path: '/',
        builder: (context, state) => const PublicHomePage(),
      ),
      GoRoute(
        path: '/sign-up',
        builder: (context, state) => const SignUpPage(),
      ),
    ],
  );

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => User(),
      child: MaterialApp.router(
        title: 'Fabric Shop',
        theme: ThemeData(
          primarySwatch: Colors.indigo,
        ),
        routerConfig: _routerConfig,
      ),
    );
  }
}

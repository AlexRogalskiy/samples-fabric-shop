import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';

import '../common/layout.dart';
import '../common/user.dart';

class PublicHomePage extends StatelessWidget {
  const PublicHomePage({super.key});

  @override
  Widget build(BuildContext context) {
    var user = context.watch<User>();

    return Layout(
      appBar: AppBar(
        title: const Text('Fabric Shop'),
        toolbarHeight: 70.0,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text('Welcome to Fabric Shop'),
            const SizedBox(height: 20),
            user.isAuthenticated
                ? Text(user.displayName)
                : Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      ElevatedButton(
                        onPressed: () {
                          GoRouter.of(context).go('/sign-up');
                        },
                        child: const Text('Sign Up'),
                      ),
                      const SizedBox(
                        width: 25,
                      ),
                      ElevatedButton(
                        onPressed: () {
                          user.signIn();
                        },
                        child: const Text('Sign In'),
                      )
                    ],
                  )
          ],
        ),
      ),
    );
  }
}

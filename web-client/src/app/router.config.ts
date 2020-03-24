import {UIRouter} from "@uirouter/angular";
import {Injector, Injectable} from "@angular/core";
import { TransitionService } from '@uirouter/core';
import { AuthService } from './auth/auth.service';

function requiresAuthHook(transitionService: TransitionService) {
  // Matches if the destination state's data property has a truthy 'requiresAuth' property
  const requiresAuthCriteria = {
    to: (state) => state.data && state.data.requiresAuth
  };

  // Function that returns a redirect for the current transition to the login state
  // if the user is not currently authenticated (according to the AuthService)
  const redirectToLogin = (transition) => {
    const authService: AuthService = transition.injector().get(AuthService);
    const $state = transition.router.stateService;
    if (!authService.check_auth()) {
      return $state.target('signin', undefined, { location: false });
    }
  };

  // Register the "requires auth" hook with the TransitionsService
  transitionService.onBefore(requiresAuthCriteria, redirectToLogin, {priority: 10});
}

/** UIRouter Config  */
export function uiRouterConfigFn(router: UIRouter, injector: Injector) {
  const transitionService = router.transitionService;
  requiresAuthHook(transitionService);
}
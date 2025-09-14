import { Component, OnInit, signal } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { filter, map, mergeMap } from 'rxjs';
import { ProgressSpinner } from 'primeng/progressspinner';
import { Toast } from 'primeng/toast';
import { UiService } from './services/ui.service';

@Component({
  selector: 'app-root',
  imports: [ButtonModule, RouterOutlet, Toast, ProgressSpinner],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  constructor(
    private titleService: Title,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public uiService: UiService,
  ) {}

  /**
 * Setting the title dynamically of each screen.
 */
  ngOnInit(): void {
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        map(() => {
          let route = this.activatedRoute;
          while (route.firstChild) route = route.firstChild;
          return route;
        }),
        filter((route) => route.outlet === 'primary'),
        mergeMap((route) => route.data)
      )
      .subscribe((data: any) => {
        if (data['title']) {
          this.titleService.setTitle(`Neo Hire - ${data['title']}`);
        }
      });
  }
}

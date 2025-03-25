import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ActivatedRoute, Data, NavigationEnd, Router } from '@angular/router';
import { filter, map, switchMap } from 'rxjs';

@Component({
  selector: 'app-footer',
  imports: [RouterModule],
  standalone: true,
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css',
})
export class FooterComponent implements OnInit {
  pageTitle: String = 'Login';

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}

  public getPageTitleFromRoute(): void {
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        map(() => {
          let route = this.activatedRoute.root;
          while (route.firstChild) {
            route = route.firstChild;
          }
          return route;
        }),
        switchMap((route) => route.data)
      )
      .subscribe((data) => {
        this.pageTitle = data['pageTitle'];
      });
  }

  ngOnInit(): void {
    this.getPageTitleFromRoute();
  }
}

import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { GameComponent } from './features/game/game.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'game/:id', component: GameComponent },
];

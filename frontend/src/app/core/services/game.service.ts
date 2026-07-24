import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Game } from "../../shared/models/game.model";
import { Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class GameService {
    private apiUrl = 'http://localhost:8080/api/game';

    constructor (private http: HttpClient){}

    getGame(idBook: number): Observable<Game> {
        const apiUrlLocal = this.apiUrl + '/' + idBook;
        return this.http.get<Game>(apiUrlLocal);
    }

    startGame(idBook: number): Observable<Game> {
        const apiUrlLocal = this.apiUrl + '/' + idBook;
        return this.http.post<Game>(apiUrlLocal, null);
    }

    updateGame(idGame: number, game: Game): Observable<Game> {
        const apiUrlLocal = this.apiUrl + '/' + idGame;
        return this.http.put<Game>(apiUrlLocal, game);
    }

}
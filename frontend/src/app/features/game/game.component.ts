import { Component, computed, OnInit, signal } from '@angular/core';
import { Book, Section, Option } from '../../shared/models/book.model';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { BookService } from '../../core/services/book.service';
import { GameService } from '../../core/services/game.service';
import { Game } from '../../shared/models/game.model';
import { catchError } from 'rxjs';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [MatIconModule, RouterLink],
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss',
})
export class GameComponent implements OnInit{
    book = signal<Book | null>(null);
    game = signal<Game | null>(null);
    message = signal<string | null>(null);
    messageStatus = signal<'success' | 'error' | null>(null);

    section = computed(() => {
        const book = this.book();
        const game = this.game();

        if(!book || !game){
            return null;
        }  

        return book.sections.find(s => s.id === game.currentSectionId) ?? null;
    });

    endGame = computed(() => {
        const section = this.section();
        if(!section){
            return false;
        } 

        return section.type === "END";
    });

    loseGame = computed(() => {
        const game = this.game();
        if(!game){
            return false;
        } 

        return game.points <= 0;
    });

    constructor(private route: ActivatedRoute, private bookService: BookService, private gameService: GameService){}

    ngOnInit(): void {
        const bookId = Number(this.route.snapshot.paramMap.get('id'));
        this.bookService.getBooks().subscribe(books => this.book.set(books.find(b => b.id === bookId) ?? null));

        this.gameService.getGame(bookId).pipe(
            catchError((error) => {
                if(error.status === 404){
                    return this.gameService.startGame(bookId);
                }
                throw error;
            })
        ).subscribe(game => this.game.set(game));

    }

    chooseOption(option: Option): void {
        this.game.update(current => current ? {...current, currentSectionId: option.gotoId } : null);
    }

    saveGame(): void {
        const game = this.game();
        if(!game) return;

        this.gameService.updateGame(game.id, game).subscribe({
            next: (updated) => {
                this.game.set(updated);
                this.message.set("Saved the game with success!");
                this.messageStatus.set('success');
            },
            error: () => {
                this.message.set("Error in the save...")
                this.messageStatus.set('error');
            }
        });
    }

    closeMesssage(): void {
        this.message.set(null);
        this.messageStatus.set(null);
    }


}


export interface Book {
    id: number;
    title: string;
    author: string;
    description: string;
    difficulty: BookDifficulty;
    types: BookType[];
    sections: Section[];
}

export type BookDifficulty = "EASY" | "MEDIUM" | "HARD";

export const BOOK_DIFFICULTY : BookDifficulty[] = [
    "EASY", "MEDIUM", "HARD"
]

export type BookType = "FANTASY" | "ADVENTURE" | "HIGH_FANTASY" | "STEAMPUNK_MYSTERY";

export const BOOK_TYPES : BookType[] = [
    "FANTASY", "ADVENTURE", "HIGH_FANTASY", "STEAMPUNK_MYSTERY"
];


export interface Section {
    id: number;
    text: string;
    type: SectionType;
    options: Option[];
}

export type SectionType = "BEGIN" | "NODE" | "END";



export interface Option {
    description: string;
    gotoId: number;
    consequence: Consequence;
}



export interface Consequence {
    type: ConsequenceType;
    value: number;
    text: string;
}

export type ConsequenceType = "LOSE_HEALTH" | "GAIN_HEALTH";
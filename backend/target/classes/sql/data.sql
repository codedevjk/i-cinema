-- 1. POPULATE MOVIES (With accurate 2025 details & Censor Ratings & Total Votes)
INSERT INTO movies (title, image_url, genre, language, description, duration_in_minutes, rating, censor_rating, total_votes) VALUES

-- Action
('War 2', 'assets/war2.jpg', 'ACTION', 'HINDI', 'Major Kabir returns for a high-stakes mission where he must face off against a lethal new adversary in a global game of espionage. The story expands the YRF Spy Universe, blending intense hand-to-hand combat with a complex plot involving betrayal and national security. As the shadows of his past loom large, Kabir must decide who he can truly trust to prevent a catastrophic international conflict.', 173, 8.2, 'UA', 1250),

('Baahubali: The Epic', 'assets/baahubali.jpg', 'ACTION', 'TELUGU', 'In the ancient kingdom of Mahishmati, a fearless young man named Shivudu falls in love with a warrior and embarks on a journey to rescue a captive queen. Along the way, he discovers his true identity as the heir to the throne and uncovers a saga of royal sibling rivalry and epic warfare. The film culminates in a massive battle sequence that changed the scale of Indian cinema forever.', 159, 8.0, 'UA', 5000),

('Jawan', 'assets/jawan.jpg', 'ACTION', 'HINDI', 'A mysterious man driven by a personal vendetta leads a team of skilled women to hijack a metro train and execute a series of high-profile heists. His actions are revealed to be a crusade against systemic corruption and a powerful arms dealer who wronged his family years ago. It is an emotional journey of a father and son seeking justice while challenging the flaws in the political and social landscape.', 169, 7.0, 'UA', 3200),

-- Comedy
('Hera Pheri', 'assets/hera_pheri.jpg', 'COMEDY', 'HINDI', 'The lives of a grumpy landlord and his two eccentric tenants take a wild turn when they receive a ransom call intended for a wealthy businessman. Driven by desperation and greed, the trio decides to intercept the money themselves, leading to a series of comedic misunderstandings and dangerous encounters with real gangsters. It remains a benchmark for situational comedy, anchored by the iconic character of Baburao Ganpatrao Apte.', 156, 8.1, 'U', 8900),

-- Drama
('Salaar: Ceasefire', 'assets/salaar.jpg', 'DRAMA', 'TELUGU', 'Set in the dystopian city-state of Khansaar, the story explores the deep-rooted friendship between Deva and Vardha amidst a violent struggle for the throne. When Vardhas father is betrayed by his own kin, Deva emerges from his self-imposed exile to become a one-man army, fulfilling a promise to protect his friend. The film is a dark, gritty drama centered on loyalty, power dynamics, and the brutal costs of maintaining an empire.', 175, 6.5, 'A', 2100),

('Dangal', 'assets/dangal.jpg', 'DRAMA', 'HINDI', 'Mahavir Singh Phogat, a former wrestling champion, defies social taboos by training his daughters, Geeta and Babita, to become world-class wrestlers in a male-dominated sport. The narrative captures their grueling journey from a small village in Haryana to the international podium at the Commonwealth Games. It is a powerful exploration of the father-daughter bond, perseverance, and the breaking of gender stereotypes through sheer determination.', 161, 8.3, 'U', 4500),

-- Horror
('Stree 2', 'assets/stree2.jpg', 'HORROR', 'HINDI', 'The peaceful town of Chanderi faces a terrifying new threat in the form of Sarkata, a headless entity that begins abducting women who have asserted their independence. Vicky and his loyal friends must team up with the mysterious woman from the first film to stop this ancient evil using unconventional methods and local folklore. This sequel blends spine-chilling horror with sharp social commentary and the signature humor that defined the franchise.', 147, 7.5, 'UA', 1800),

-- Romance
('Saiyaraa', 'assets/saiyaraa.jpg', 'ROMANCE', 'HINDI', 'A poetic and visually stunning love story that follows two souls from different backgrounds who find solace in each other’s company during a shared crisis. As they navigate the complexities of their past traumas and societal expectations, their bond deepens into a transformative romance that challenges their worldview. The film emphasizes the idea that love is not just about finding the right person, but about healing together in a broken world.', 156, 8.1, 'UA', 560),

-- Thriller
('Drishyam', 'assets/drishyam.jpg', 'THRILLER', 'HINDI', 'Vijay Salgaonkar, a cable operator and movie buff, uses his knowledge of cinema to protect his family after they accidentally commit a crime to defend themselves. He meticulously constructs an elaborate alibi that keeps the police guessing, even as a determined IG mother searches for her missing son. The film is a masterclass in suspense, showcasing how an ordinary man’s wit can challenge the might of the entire legal system.', 163, 8.2, 'UA', 6700),

-- Sci-Fi
('Avengers: Endgame', 'assets/endgame.jpg', 'SCIFI', 'ENGLISH', 'Following the catastrophic snap of Thanos that wiped out half of all life, the remaining Avengers must find a way to travel through time and retrieve the Infinity Stones. Their mission is a desperate "time heist" that requires them to revisit key moments in their history to undo the decimation and bring back their fallen comrades. This epic conclusion marks the end of an era, focusing on sacrifice, heroism, and the ultimate battle for the universe.', 181, 8.4, 'UA', 12000),

-- Malayalam (Action Comedy)
('Aavesham', 'assets/aavesham.jpg', 'COMEDY', 'MALAYALAM', 'Three engineering students in Bengaluru find themselves constantly bullied by their seniors and decide to seek help from a local eccentric gangster named Ranga. As they get closer to Ranga and his chaotic lifestyle, they realize that his protection comes with a heavy price and a series of explosive, unpredictable consequences. The film blends dark humor with high-octane action, anchored by a career-best performance that explores the fine line between friendship and fear.', 159, 7.9, 'UA', 950),

-- Kannada (Mythological Action)
('Kantara: Chapter 1', 'assets/kantara.jpg', 'DRAMA', 'KANNADA', 'Set centuries before the original film, this epic prequel explores the divine origins of the Kantara forest and the ancient pact between the Kadamba dynasty and the guardian deities Panjurli and Guliga. The narrative follows a legendary warrior who must defend his tribe and their sacred land against the greed of an expanding kingdom and forbidden rituals. It is a visually spectacular journey into folklore, blending visceral action with deep spiritual themes and the raw intensity of the Bhoota Kola tradition.', 168, 9.3, 'UA', 3400),

-- Animation
('Demon Slayer: Infinity Castle', 'assets/demon_slayer.jpg', 'ANIMATION', 'ENGLISH', 'Tanjiro Kamado and the remaining Hashira find themselves trapped within the ever-shifting, gravity-defying corridors of the Infinity Castle, the stronghold of the Demon King Muzan. This first part of the final trilogy features breathtaking animation as the slayers engage in intense, high-stakes duels against the most powerful Upper Rank demons. It is a visually spectacular journey of survival and revenge that sets the stage for the final end of the demon era.', 155, 8.5, 'UA', 1500);

-- 2. POPULATE THEATRES (Bengaluru & Mysuru)
INSERT INTO theatres (name, location, city) VALUES
('PVR: Nexus (Forum Mall)', 'Koramangala, Bengaluru', 'Bengaluru'),
('Cinepolis: Lulu Mall', 'Rajajinagar, Bengaluru', 'Bengaluru'),
('PVR: VR Bengaluru', 'Whitefield Road, Bengaluru', 'Bengaluru'),
('DRC Cinemas', 'BM Habitat Mall, Mysuru', 'Mysuru'),
('INOX: Mall of Mysuru', 'MG Road, Mysuru', 'Mysuru'),
('PVR: Garuda Mall', 'Albert Victor Road, Mysuru', 'Mysuru');

-- 3. RANDOMIZED SHOW GENERATION (Varied 2-5 shows/day for Today, +1, +2)
-- Generates a mix of morning, afternoon, and evening slots dynamically.
INSERT INTO shows (movie_id, theatre_id, show_date, show_time)
SELECT 
    m.id, 
    t.id, 
    d.target_date, 
    s.slot_time
FROM movies m
CROSS JOIN theatres t
CROSS JOIN (
    SELECT CURRENT_DATE AS target_date, 1 as day_num
    UNION ALL SELECT DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), 2
    UNION ALL SELECT DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), 3
) d
CROSS JOIN (
    SELECT '09:00:00' as slot_time, 1 as slot_id
    UNION ALL SELECT '10:15:00', 2
    UNION ALL SELECT '12:30:00', 3
    UNION ALL SELECT '14:45:00', 4
    UNION ALL SELECT '16:00:00', 5
    UNION ALL SELECT '18:30:00', 6
    UNION ALL SELECT '20:15:00', 7
    UNION ALL SELECT '22:00:00', 8
) s
WHERE 
    (
        -- Today (Day 1): High density (30-45% chance -> 3-4 shows out of 8 slots)
        (d.day_num = 1 AND (m.id * 7 + t.id * 3 + s.slot_id * 11) % 100 < 45)
        OR
        -- Tomorrow (Day 2): Medium density (20-35% chance -> 2-3 shows)
        (d.day_num = 2 AND (m.id * 7 + t.id * 3 + s.slot_id * 11) % 100 < 30)
        OR
        -- Day After (Day 3): Low density (5-20% chance -> 0-2 shows)
        (d.day_num = 3 AND (m.id * 7 + t.id * 3 + s.slot_id * 11) % 100 < 15)
    );

-- 4. INITIALIZE SEATS (Removed to allow Dynamic Backend Generation with Random Occupancy)
-- The backend ShowServiceImpl will now automatically generate 100 seats 
-- with random BOOKED/AVAILABLE status when a user views a show.

-- 5. POPULATE USERS
INSERT INTO users (name, email, password, phone) VALUES 
('Jagmohan Kushwaha', 'jagmohan.jk11@gmail.com', '1234567890', '9170961122'),
('Bob Smith', 'bob@example.com', 'password123', '8765432109'),
('Charlie Brown', 'charlie@example.com', 'password123', '7654321098');

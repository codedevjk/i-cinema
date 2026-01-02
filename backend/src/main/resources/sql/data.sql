INSERT INTO movies (title, image_url, genre, language, description, duration_in_minutes, rating, censor_rating, total_votes) VALUES

-- A mix of 2024/2025 Blockbusters and Classic Favorites
('Dhurandhar', 'assets/dhurandhar.jpg', 'ACTION', 'HINDI', 'A top-tier secret agent is pulled out of a peaceful retirement when a ghost from his past threatens to launch a global cyber-terror attack. As he assembles a team of rogue specialists, he must navigate a web of high-level government corruption and lethal double agents across three continents. This explosive spy thriller became an all-time blockbuster in 2025.', 158, 8.2, 'UA', 99),

('Baahubali: The Epic', 'assets/baahubali.jpg', 'ACTION', 'TELUGU', 'In the ancient kingdom of Mahishmati, a young man discovers his royal heritage and the legacy of a legendary warrior father in a kingdom torn by war. He must confront a tyrannical king and reclaim his throne to restore justice to his people.', 159, 8.0, 'UA', 95),

('Kalki 2898 AD', 'assets/kalki.jpg', 'SCIFI', 'TELUGU', 'In a dystopian future where the city of Kasi is the last standing civilization, a group of rebels seeks to protect a mysterious woman believed to carry a savior. The journey blends ancient Indian mythology with futuristic technology, featuring an epic confrontation across eras.', 181, 7.6, 'UA', 98),

('Hera Pheri', 'assets/hera_pheri.jpg', 'COMEDY', 'HINDI', 'The lives of a grumpy landlord and his two tenants take a wild turn when they receive a ransom call intended for a wealthy businessman. Driven by greed and desperation, the trio decides to intercept the money, leading to iconic comedic misunderstandings.', 156, 8.1, 'U', 91),

('Pushpa 2: The Rule', 'assets/pushpa2.jpg', 'ACTION', 'TELUGU', 'Pushpa Raj has expanded his empire across borders, but his success draws the ire of international syndicates and his relentless nemesis, SP Shekhawat. The film follows his journey from a local smuggler to an unstoppable kingpin, showcasing the high price of maintaining absolute power.', 204, 8.5, 'UA', 100),

('Dangal', 'assets/dangal.jpg', 'DRAMA', 'HINDI', 'Mahavir Singh Phogat, a former wrestling champion, defies social taboos by training his daughters to become world-class wrestlers in a male-dominated sport. The narrative captures their grueling journey from a small village to the international podium at the Commonwealth Games.', 161, 8.3, 'U', 98),

('Chhaava', 'assets/chhaava.jpg', 'DRAMA', 'HINDI', 'Based on the life of Chhatrapati Sambhaji Maharaj, the film portrays the legendary Maratha warrior’s struggle to uphold his father’s legacy against the Mughal Empire. It captures the intense political maneuvers and grand-scale battles of a leader who chose martyrdom over betrayal.', 165, 8.4, 'UA', 95),

('Drishyam', 'assets/drishyam.jpg', 'THRILLER', 'HINDI', 'A common man takes desperate measures to save his family from the dark side of the law after they commit an unexpected crime. He meticulously constructs an elaborate alibi that keeps the police guessing, proving how a man’s wit can challenge the legal system.', 163, 8.2, 'UA', 89),

('Stree 2', 'assets/stree2.jpg', 'HORROR', 'HINDI', 'The residents of Chanderi find themselves haunted once again, but this time by a headless entity known as Sarkata that targets independent-minded women. The group of friends must seek the help of a mysterious woman to uncover the origin of this ancient curse.', 147, 7.5, 'UA', 97),

('Avengers: Endgame', 'assets/endgame.jpg', 'SCIFI', 'ENGLISH', 'After the devastating snap of Thanos wiped out half of all life, the remaining Avengers must find a way to travel through time and retrieve the Infinity Stones. Their mission is a desperate heist to undo the decimation and bring back their fallen comrades.', 181, 8.4, 'UA', 99),

('War 2', 'assets/war2.jpg', 'ACTION', 'HINDI', 'Major Kabir returns for a high-stakes mission where he must face off against a lethal new adversary in a global game of espionage. The story blends intense hand-to-hand combat with a complex plot involving betrayal and national security in the YRF Spy Universe.', 173, 8.2, 'UA', 82),

('Jawan', 'assets/jawan.jpg', 'ACTION', 'HINDI', 'A mysterious man driven by a personal vendetta leads a team of skilled women to execute high-profile heists as a crusade against systemic corruption. It is an emotional journey of a father and son seeking justice while challenging the flaws in the social landscape.', 169, 7.0, 'UA', 78),

('Inside Out 2', 'assets/inside_out2.jpg', 'ANIMATION', 'ENGLISH', 'As Riley enters her teenage years, her internal control center undergoes a major renovation to make room for new emotions like Anxiety and Envy. When these new arrivals take over, Joy and the original crew struggle to maintain Riley’s sense of self.', 96, 7.7, 'U', 94),

('Salaar: Ceasefire', 'assets/salaar.jpg', 'DRAMA', 'TELUGU', 'Set in the dystopian city-state of Khansaar, the story explores the deep-rooted friendship between Deva and Vardha amidst a violent struggle for the throne. Deva emerges from his self-imposed exile to become a one-man army to protect his friend.', 175, 6.5, 'A', 64),

('Kantara: Chapter 1', 'assets/kantara.jpg', 'DRAMA', 'KANNADA', 'Set centuries before the original film, this epic prequel explores the divine origins of the Kantara forest and the ancient pact between a legendary warrior and sacred deities. It is a visually spectacular journey into folklore and spiritual themes.', 168, 9.3, 'UA', 92),

('Demon Slayer: Infinity Castle', 'assets/demon_slayer.jpg', 'ANIMATION', 'ENGLISH', 'Tanjiro Kamado and the remaining Hashira find themselves trapped within the ever-shifting, gravity-defying corridors of the Infinity Castle. They engage in high-stakes duels against the most powerful Upper Rank demons in a final battle for survival.', 155, 8.5, 'UA', 87),

('Aavesham', 'assets/aavesham.jpg', 'COMEDY', 'MALAYALAM', 'Three engineering students seek help from an eccentric local gangster named Ranga to deal with their bullies. As they enter his chaotic world, they realize his protection comes with unpredictable consequences in this high-octane action comedy.', 159, 7.9, 'UA', 74),

('Saiyaraa', 'assets/saiyaraa.jpg', 'ROMANCE', 'HINDI', 'An intense, soul-stirring love story that explores the depths of heartbreak and the journey toward healing for two souls from different backgrounds. As they navigate their past traumas, their bond deepens into a transformative romance that challenges their worldview.', 156, 8.1, 'UA', 42);



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

-- Users
-- password in plaintext: password
INSERT INTO public.users (user_id, user_boss_id_fk, user_creation_date, user_email, user_active,
                          user_name, user_password, user_removed, user_role, user_surname)
VALUES ('20ebd0bf-f4b8-4ad0-b1eb-01d373250368', null, '2020-06-03 10:17:43.106056', 'test@email.com', true,
        'Jan', '$2a$10$7zS3RPTW1e8mCFDnuc2GU.uivheGrT8nQckq.P2xYk.i.ebQeaavC', false, 'BOSS', 'Kowalski'),
       ('ef3f1790-433e-43e1-8b14-4a0b7e27cca6', '20ebd0bf-f4b8-4ad0-b1eb-01d373250368',
        '2020-06-03 10:17:43.106056', 'user@email.com', true, 'Jan', '$2a$10$7zS3RPTW1e8mCFDnuc2GU.uivheGrT8nQckq.P2xYk.i.ebQeaavC', false, 'EMPLOYEE', 'Worker'),
       ('a8226bdc-595b-4be3-b2b6-164788475f41', '20ebd0bf-f4b8-4ad0-b1eb-01d373250368',
        '2020-06-03 10:17:43.106056', 'user2@email.com', true, 'Jan', '$2a$10$7zS3RPTW1e8mCFDnuc2GU.uivheGrT8nQckq.P2xYk.i.ebQeaavC', false, 'EMPLOYEE', 'Bossman');

-- Cars
INSERT INTO public.car (car_id, car_brand, car_creation_date, car_date_of_last_review,
                        car_date_of_next_review, car_date_of_next_technical_examination, car_model,
                        car_removed, car_status, car_type, car_production_year)
VALUES ('ae98efef-0f84-46e5-bb57-26c69d6b4042', 'BMW', current_timestamp, current_date,
        current_date, current_date, 'F10', false, 'AVAILABLE', 'PREMIUM_CAR', 2019),
       ('883116b2-f840-4c74-9c1c-ca64d1bf4185', 'BMW', current_timestamp, current_date,
        current_date, current_date, 'F30', false, 'AVAILABLE', 'PREMIUM_CAR', 2019),
       ('135ea89b-fd7e-41b1-809f-84d32f823bcd', 'Opel', current_timestamp, current_date,
        current_date, current_date, 'Astra', false, 'AVAILABLE', 'CAR', 2020),
       ('3cf4af86-cde5-4a30-82a4-cb3e48058ac4', 'Opel', current_timestamp, current_date,
        current_date, current_date, 'Astra', false, 'AVAILABLE', 'CAR', 2018),
       ('e7252fec-57f6-45d1-8dcb-9d7bd27e1682', 'Opel', current_timestamp, current_date,
        current_date, current_date, 'Insignia', false, 'AVAILABLE', 'PREMIUM_CAR', 2018),
       ('f077c68e-313c-4091-81bf-2805d81d4a13', 'Opel', current_timestamp, current_date,
        current_date, current_date, 'Insignia', false, 'LOAN', 'PREMIUM_CAR', 2019);

-- Rental
INSERT INTO public.rental (rental_id, rental_application_date, rental_car_condition_description,
    rental_creation_date, rental_distance_traveled, rental_boss_accepted, rental_positively_received,
    rental_actual_kilometer_in_car_meter, rental_planned_end_date, rental_planned_start_date,
    rental_received_description, rental_removed, rental_target, rental_car_fk, lender_user_fk,
    rental_is_returned, rental_return_date)
VALUES ('fd5903a1-d78f-4fe1-aeef-974c6ebab706', current_date, null, current_date, null, null,
         null, null, current_date, current_date , null, false, 'aaa',
        'ae98efef-0f84-46e5-bb57-26c69d6b4042', '20ebd0bf-f4b8-4ad0-b1eb-01d373250368', true, current_timestamp ),
        ('1a586b98-8951-45d7-9a22-56807262df17', current_date, null, current_date, null, null,
         null, null, current_date, current_date , null, false, 'aaa',
        'f077c68e-313c-4091-81bf-2805d81d4a13', '20ebd0bf-f4b8-4ad0-b1eb-01d373250368', false, current_timestamp );